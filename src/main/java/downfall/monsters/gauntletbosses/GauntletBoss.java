package downfall.monsters.gauntletbosses;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import downfall.monsters.NeowBoss;
import downfall.powers.gauntletpowers.OnDeathEveryoneBuffer;

public abstract class GauntletBoss extends AbstractMonster {

    public boolean isAttacking;
    public boolean isThird;
    public GauntletBoss ally1;
    public GauntletBoss ally2;

    public GauntletBoss(String name, String id, int maxHealth, float hb_x, float hb_y, float hb_w, float hb_h, String imgUrl, float offsetX, float offsetY) {
        super(name, id, maxHealth, hb_x, hb_y, hb_w, hb_h, imgUrl, offsetX, offsetY);
    }

    public String moveName(String ID) {
        return moveName(ID, "");
    }

    public String moveName(String ID, String ID2) {
        if (ID2 == "") {

            return CardLibrary.getCard(ID).name;
        } else {
            return CardLibrary.getCard(ID).name + " / " + CardLibrary.getCard(ID2).name;
        }
    }

    @Override
    public void usePreBattleAction() {
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new DexterityPower(this, 3), 3));
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(this, this, new StrengthPower(this, 3), 3));
    }

    @Override
    public void die() {
        super.die();
        addToBot(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                boolean endCombat = true;
                for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
                    if (q instanceof GauntletBoss && !q.isDying && !q.isDead) {
                        endCombat = false;
                    }
                }
                if (endCombat) {
                    addToTop(new AbstractGameAction() {
                        @Override
                        public void update() {
                            isDone = true;
                            NeowBoss.neowboss.takeTurn();
                        }
                    });
                }
            }
        });
    }
}
