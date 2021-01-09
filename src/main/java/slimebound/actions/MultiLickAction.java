package slimebound.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;


public class MultiLickAction extends AbstractGameAction {
    private static final float DURATION = 0.01F;
    private static final float POST_ATTACK_WAIT_DUR = 0.2F;
    private DamageInfo info;
    private int numTimes;
    private int energyOnUse = -1;
    private AbstractPlayer p;


    public MultiLickAction(AbstractPlayer player, AbstractCreature target, DamageInfo info, int energyOnuse) {

        this.info = info;

        this.target = target;

        this.actionType = ActionType.SPECIAL;

        this.attackEffect = AttackEffect.POISON;

        this.duration = 0.01F;

        this.numTimes = numTimes;
        this.p = player;
        this.energyOnUse = energyOnUse;

    }


    public void update() {

        int effect = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            effect = this.energyOnUse;
        }

        if (this.p.hasRelic("Chemical X")) {
            effect += 2;
            this.p.getRelic("Chemical X").flash();
        }

        if (effect > 0) {

            for (int i = 0; i < effect; ++i) {
                this.target = AbstractDungeon.getMonsters().getRandomMonster(true);
                if (this.target == null) {
                    this.isDone = true;
                } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                    AbstractDungeon.actionManager.clearPostCombatActions();
                    this.isDone = true;
                } else {

                    if (this.target.currentHealth > 0) {
                        // this.target.damageFlash = true;
                        // this.target.damageFlashFrames = 4;
                        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, this.attackEffect));
                        // this.info.genPreview(this.info.owner, this.target);
                        // this.target.damage(this.info);
                        AbstractDungeon.actionManager.addToBottom(new DamageAction(target, this.info, AbstractGameAction.AttackEffect.BLUNT_HEAVY));

                        if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                            --this.numTimes;


                        }

                    }


                }


            }
            for (int i2 = 0; i2 < effect; ++i2) {

                AbstractDungeon.actionManager.addToBottom(new RandomLickCardAction(false));


            }
            this.p.energy.use(EnergyPanel.totalCount);

        }
        this.isDone = true;
    }
}


