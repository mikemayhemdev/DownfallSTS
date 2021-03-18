package automaton.powers;

import automaton.cards.FunctionCard;
import automaton.cards.WhirlingStrike;
import automaton.vfx.PiercingShotEffect;
import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.CardModifierPatches;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.NonStackablePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class FullReleasePower extends AbstractAutomatonPower implements NonStackablePower {
    public static final String NAME = "FullRelease";
    public static final String POWER_ID = makeID(NAME);
    public static final PowerType TYPE = PowerType.BUFF;
    public static final boolean TURN_BASED = false;

    private AbstractCard stored;

    public FullReleasePower(AbstractCard c) {
        super(NAME, TYPE, TURN_BASED, AbstractDungeon.player, null, -1);
        stored = c;
        updateDescription();
    }

    @Override
    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            flash();
            AbstractCard q = stored.makeStatEquivalentCopy();
            if (q instanceof FunctionCard) {
                for (AbstractCard r : ((FunctionCard) q).cards()) {
                    r.resetAttributes();
                }
                addToBot(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        for (int i = ((FunctionCard) q).cards().size() - 1; i >= 0; i--) {
                            AbstractCard c = ((FunctionCard) q).cards().get(i);
                            c.resetAttributes();
                            addToTop(new AbstractGameAction() {
                                @Override
                                public void update() {
                                    isDone = true;
                                    c.resetAttributes();
                                    AbstractMonster m = AbstractDungeon.getRandomMonster();
                                    if (c instanceof WhirlingStrike) {
                                        //Shoddy fix for this SINGLE CARD
                                        AbstractDungeon.actionManager.addToBottom(new SFXAction("ATTACK_HEAVY", .75F, true));
                                        AbstractDungeon.actionManager.addToBottom(new VFXAction(AbstractDungeon.player, new PiercingShotEffect(AbstractDungeon.player.hb.cX, AbstractDungeon.player.hb.cY, Settings.WIDTH + 100F, AbstractDungeon.player.hb.cY), 0F));
                                        addToBot(new DamageAllEnemiesAction(AbstractDungeon.player, DamageInfo.createDamageMatrix(c.baseDamage, true), DamageInfo.DamageType.THORNS, AttackEffect.NONE));
                                    } else {
                                        c.use(AbstractDungeon.player, m);
                                    }
                                }
                            });
                        }
                    }
                });
            }
        }
    }


    @Override
    public void updateDescription() {
        if (stored == null) {
            description = "ERROR - PLEASE REPORT BUG";
        } else {
            String s = DESCRIPTIONS[0] + CardModifierPatches.CardModifierOnCreateDescription.calculateRawDescription(stored, stored.rawDescription);
            description = s.replaceAll("bronze:", "#y").replaceAll("\\*", "#y");
        }
    }
}
