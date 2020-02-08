package slimebound.powers;

import com.evacipated.cardcrawl.mod.stslib.actions.defect.EvokeSpecificOrbAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.ShieldParticleEffect;
import slimebound.orbs.SpawnedSlime;


public class SlimeSacrificePower extends AbstractPower {
    public static final String POWER_ID = "Slimebound:ProtectTheBoss";
    public static final String NAME = "Slime Sacrifice";
    public static final String IMG = "powers/SlimeSacrificeS.png";
    public static PowerType POWER_TYPE = PowerType.BUFF;
    public static String[] DESCRIPTIONS;
    private AbstractCreature source;
    private int startingStack;

    public SlimeSacrificePower(AbstractCreature owner, int bufferAmt) {
        this.name = NAME;

        this.ID = POWER_ID;
        this.amount = bufferAmt;

        this.owner = owner;


        this.img = new com.badlogic.gdx.graphics.Texture(slimebound.SlimeboundMod.getResourcePath(IMG));

        this.type = POWER_TYPE;

        this.DESCRIPTIONS = CardCrawlGame.languagePack.getPowerStrings(this.ID).DESCRIPTIONS;

        this.name = CardCrawlGame.languagePack.getPowerStrings(this.ID).NAME;
        updateDescription();

    }


    public int onAttacked(DamageInfo info, int damageAmount) {

        if (info.type == DamageInfo.DamageType.NORMAL) {
            if (info.owner != AbstractDungeon.player) {
                if (damageAmount > AbstractDungeon.player.currentBlock) {
                    if (!AbstractDungeon.player.orbs.isEmpty()) {
                        for (AbstractOrb o : AbstractDungeon.player.orbs) {

                            if (o instanceof SpawnedSlime) {

                                SpawnedSlime s = (SpawnedSlime) o;
                                //s.noEvokeBonus = true;
                                this.flash();
                                com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new VFXAction(new ShieldParticleEffect(o.cX, o.cY)));
                                com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToTop(new com.megacrit.cardcrawl.actions.common.ReducePowerAction(this.owner, this.owner, this.ID, 1));
                                AbstractDungeon.actionManager.addToTop(new EvokeSpecificOrbAction(o));
                                return AbstractDungeon.player.currentBlock;

                            }
                        }
                    }
                }
            }
        }
        return damageAmount;
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }


    public void updateDescription() {
        if (this.amount <= 1) {
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = (DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2]);
        }
    }
}


