package champ.cards;

import champ.powers.ResolvePower;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class AdrenalArmor extends AbstractChampCard {

    public final static String ID = makeID("AdrenalArmor");

    //stupid intellij stuff skill, self, common

    private static final int BLOCK = 9;
    private static final int MAGIC = 2;

    public AdrenalArmor() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        myHpLossCost = 2;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        loseHP(magicNumber);
        applyToSelf(new ResolvePower(magicNumber));
        if (bcombo() && !this.purgeOnUse) {
            AbstractCard r = this;
            atb(new AbstractGameAction() {
                @Override
                public void update() {
                    isDone = true;
                    GameActionManager.queueExtraCard(r, m);
                }
            });
            if (upgraded) {
                atb(new AbstractGameAction() {
                    @Override
                    public void update() {
                        isDone = true;
                        GameActionManager.queueExtraCard(r, m);
                    }
                });
            }
        }
    }

    public void upp() {
        rawDescription = UPGRADE_DESCRIPTION;
        initializeDescription();
    }
}