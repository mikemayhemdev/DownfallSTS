package awakenedOne.cards;

import automaton.cards.goodstatus.IntoTheVoid;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.status.VoidCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static awakenedOne.AwakenedOneMod.*;

public class Gloomguard extends AbstractAwakenedCard {
    public final static String ID = makeID(Gloomguard.class.getSimpleName());
    // intellij stuff power, self, rare, , , , , ,

    public Gloomguard() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 8;
        loadJokeCardImage(this, makeBetaCardPath(Gloomguard.class.getSimpleName() + ".png"));
    }

    public static boolean checkVoid() {
        boolean hasVoid = false;
        for (AbstractCard c : AbstractDungeon.player.hand.group) {
            if (c instanceof VoidCard || c instanceof IntoTheVoid) {
                hasVoid = true;
                break;
            }
        }
        return hasVoid;
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        blck();
    }

    @Override
    public void triggerOnGlowCheck() {
        if (checkVoid()) {
            this.glowColor = AbstractCard.GOLD_BORDER_GLOW_COLOR.cpy();
        } else {
            this.glowColor = AbstractCard.BLUE_BORDER_GLOW_COLOR.cpy();
        }
    }

    @Override
    public void initializeDescription() {
        super.initializeDescription();
        this.keywords.add(GameDictionary.VOID.NAMES[0].toLowerCase());
    }

    public void upp() {
        upgradeBlock(3);
    }
}