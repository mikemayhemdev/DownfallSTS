package sneckomod.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.BiteEffect;
import sneckomod.SneckoMod;

import java.util.ArrayList;

public class MakeshiftBlade extends AbstractSneckoCard {
    // todo: try to make this one glow later
    public static final String ID = SneckoMod.makeID("MakeshiftBlade");

    // Card constants
    private static final int DAMAGE = 12;
    private static final int COST = 1;
    private static final int MAGIC = 3; // Initial debuff requirement
    private static final int UPGRADE_MAGIC = -1; // Reduces debuff requirement by 1


    public MakeshiftBlade() {
        super(ID, COST, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = DAMAGE;
        baseMagicNumber = magicNumber = MAGIC;
        baseSilly = silly = 3;
        SneckoMod.loadJokeCardImage(this, "MakeshiftBlade.png");
    }

    public static boolean cardListDuplicate(ArrayList<AbstractCard> cardsList, AbstractCard card) {
        System.out.println("DEBUG: Checking for duplicate: " + card.name);
        for (AbstractCard alreadyHave : cardsList) {
            if (alreadyHave.cardID.equals(card.cardID)) {
                System.out.println("DEBUG: Duplicate detected: " + card.name);
                return true;
            }
        }
        System.out.println("DEBUG: No Duplicate detected: " + card.name);
        return false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new VFXAction(new BiteEffect(m.hb.cX, m.hb.cY), 0.3F));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (m != null && m.powers.stream().filter(power -> power.type == AbstractPower.PowerType.DEBUFF).count() >= magicNumber) {
            addToBot(new DrawCardAction(p, this.silly));
        }
    }

    @Override
    public void onObtainCard() {
        System.out.println("DEBUG: Took Makeshift Blade.");
        ArrayList<AbstractCard> cardsToReward = new ArrayList<>();
        while (cardsToReward.size() < 3) {
            AbstractCard newCard = SneckoMod.getOffClassCardMatchingPredicate(c -> (!c.name.contains("I'll Take That") && (c.rawDescription.contains("Apply") || c.rawDescription.contains("apply") || c.rawDescription.contains("applies") || c.rawDescription.contains("Lick") || c.rawDescription.contains("Debuff") || c.rawDescription.contains("Steal") || c.name.contains("Disarm") || c.name.contains("Choke") || c.name.contains("Talk to the Hand") || c.name.contains("Cursed Wail") || c.name.contains("Undervolt") || c.name.contains("Piercing Wail") || c.name.contains("Billow"))
            ) && c.rarity == AbstractCard.CardRarity.UNCOMMON);
            System.out.println("DEBUG: Card generated: " + newCard.name);
            if (!cardListDuplicate(cardsToReward, newCard)) {
                cardsToReward.add(newCard.makeCopy());
                System.out.println("DEBUG: Card added: " + newCard.name);
            }
        }

        SneckoMod.addGift(cardsToReward);
        ;
    }

    @Override
    public void upgrade() {
        if (!upgraded) {
            upgradeName();
            upgradeMagicNumber(UPGRADE_MAGIC);
            upgradeDamage(4);
        }
    }
}
