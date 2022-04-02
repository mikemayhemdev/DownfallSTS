package timeeater.cards;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import timeeater.cardmods.IncreasedBlockMod;
import timeeater.cardmods.IncreasedDamageMod;
import timeeater.cards.AbstractTimeEaterCard;
import timeeater.suspend.SuspendHelper;

import static timeeater.TimeEaterMod.makeID;
import static timeeater.util.Wiz.*;

public class InfuseDefense extends AbstractTimeEaterCard {
    public final static String ID = makeID("InfuseDefense");
    // intellij stuff skill, self, common, , , 5, 2, 2, 1

    public InfuseDefense() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 5;
        baseMagicNumber = magicNumber = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                for (AbstractCard q : SuspendHelper.suspendGroup.group) {
                    if (q.baseBlock > -1) q.superFlash();
                    CardModifierManager.addModifier(q, new IncreasedBlockMod(magicNumber));
                }
            }
        });
    }

    public void upp() {
        upgradeBlock(2);
        upgradeMagicNumber(1);
    }
}