package theHexaghost.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class GhostShield extends AbstractHexaCard {

    public final static String ID = makeID("GhostShield");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;

    public GhostShield() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseBlock = BLOCK;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        atb(new AbstractGameAction() {
            @Override
            public void update() {
                isDone = true;
                boolean bruh = false;
                for (AbstractCard c : p.hand.group) {
                    if (c.isEthereal)
                        bruh = true;
                }
                if (bruh)
                    blck();
            }
        });
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
        }
    }
}