package theHexaghost.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import theHexaghost.HexaMod;

public class GhostShield extends AbstractHexaCard {

    public final static String ID = makeID("GhostShield");

    //stupid intellij stuff SKILL, SELF, UNCOMMON

    private static final int BLOCK = 5;
    private static final int MAGIC = 1;
    private static final int UPG_BLOCK = 1;

    public GhostShield() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = BLOCK;
        baseMagicNumber = magicNumber = MAGIC;
        isEthereal = true;
        tags.add(HexaMod.AFTERLIFE);
        HexaMod.loadJokeCardImage(this, "GhostShield.png");
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
        for (AbstractMonster q : AbstractDungeon.getCurrRoom().monsters.monsters) {
            if (!q.isDeadOrEscaped()) {
                applyToEnemy(q, new WeakPower(q, magicNumber, false));
            }
        }
    }

    @Override
    public void afterlife() {
        use(AbstractDungeon.player, null);
    }

    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeBlock(UPG_BLOCK);
            upgradeMagicNumber(1);
        }
    }
}