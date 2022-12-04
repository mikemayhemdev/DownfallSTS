package slimebound.cards;


import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import slimebound.SlimeboundMod;
import slimebound.patches.AbstractCardEnum;
import slimebound.vfx.LeechEffect;


public class LeechLife extends AbstractSlimeboundCard {
    public static final String ID = "Slimebound:LeechLife";
    public static final String NAME;
    public static final String DESCRIPTION;
    public static final String IMG_PATH = "cards/leechingstrike.png";
    private static final CardStrings cardStrings;

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
        NAME = cardStrings.NAME;
        DESCRIPTION = cardStrings.DESCRIPTION;

    }

    public LeechLife() {
        super(ID, NAME, SlimeboundMod.getResourcePath(IMG_PATH), 2, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.SLIMEBOUND, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage = 8;
        this.isEthereal = true;
        this.exhaust = true;
        tags.add(CardTags.HEALING);
        SlimeboundMod.loadJokeCardImage(this, "LeechLife.png");
    }

    @Override
    public void triggerOnGlowCheck() {
        slimedGlowCheck();
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                this.tickDuration();
                if (this.isDone) {
                    int healAmount = 0;

                    if (!m.isDying && m.currentHealth > 0 && !m.isEscaping) {
                        m.damage(new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL));
                        if (m.lastDamageTaken > 0) {
                            healAmount = m.lastDamageTaken;
/*
                            for(int j = 0; j < target.lastDamageTaken / 2 && j < 10; ++j) {
                                this.addToBot(new VFXAction(new FlyingOrbEffect(target.hb.cX, target.hb.cY)));
                            }
*/
                        }
                    }

                    if (healAmount > 0) {
                        if (!Settings.FAST_MODE) {
                            this.addToBot(new WaitAction(0.3F));
                        }

                        AbstractDungeon.actionManager.addToBottom(new VFXAction(new LeechEffect(m.hb.cX, m.hb.cY, p.hb.cX, p.hb.cY, healAmount, new Color(0.75F, 0F, 0F, 1F)), 0.15F));
                        this.addToBot(new HealAction(p, p, healAmount));
                    }

                    if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                        AbstractDungeon.actionManager.clearPostCombatActions();
                    }

                    this.addToTop(new WaitAction(0.1F));
                }
            }
        });
    }

    public AbstractCard makeCopy() {
        return new LeechLife();
    }

    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(2);
        }
    }
}


